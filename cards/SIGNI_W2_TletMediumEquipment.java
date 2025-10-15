package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_W2_TletMediumEquipment extends Card {

    public SIGNI_W2_TletMediumEquipment()
    {
        setImageSets("WX24-P1-054");

        setOriginalName("中装　トレット");
        setAltNames("チュウソウトレット Chuusou Toretto");
        setDescription("jp",
                "@U $T1：あなたの他の＜アーム＞のシグニがバトルによってシグニ１体をバニッシュしたとき、カードを１枚引く。"
        );

        setName("en", "Tlet, Medium Equipment");
        setDescription("en",
                "@U $T1: When 1 of your other <<Arm>> SIGNI banishes a SIGNI in battle, draw 1 card."
        );

		setName("zh_simplified", "中装 手甲");
        setDescription("zh_simplified", 
                "@U $T1 :当你的其他的<<アーム>>精灵因为战斗把精灵1只破坏时，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceAbility() == null && !isOwnCard(caller) && isOwnCard(getEvent().getSourceCardIndex()) &&
                   getEvent().getSourceCardIndex().getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.ARM) &&
                   getEvent().getSourceCardIndex() != getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            draw(1);
        }
    }
}
