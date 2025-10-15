package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.stock.StockAbilityShoot;

public final class SIGNI_W2_JiganemaruMediumEquipment extends Card {

    public SIGNI_W2_JiganemaruMediumEquipment()
    {
        setImageSets("WX24-P4-058");

        setOriginalName("中装　ジガネマル");
        setAltNames("チュウソウジガネマル Chuusou Jiganemaru");
        setDescription("jp",
                "@C：【シュート】\n" +
                "@U $T1：あなたのシグニがバトルによってシグニ１体をバニッシュしたとき、次の対戦相手のターン終了時まで、このシグニのパワーを＋5000する。"
        );

        setName("en", "Jiganemaru, Medium Equipment");
        setDescription("en",
                "@C: [[Shoot]]\n" +
                "@U $T1: When your SIGNI banishes a SIGNI by battle, until the end of your opponent's next turn, this SIGNI gets +5000 power."
        );

		setName("zh_simplified", "中装 治金丸");
        setDescription("zh_simplified", 
                "@C :[[击落]]\n" +
                "@U $T1 :当你的精灵因为战斗把精灵1只破坏时，直到下一个对战对手的回合结束时为止，这只精灵的力量+5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerStockAbility(new StockAbilityShoot());

            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceAbility() == null && isOwnCard(getEvent().getSource()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            gainPower(getCardIndex(), 5000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
