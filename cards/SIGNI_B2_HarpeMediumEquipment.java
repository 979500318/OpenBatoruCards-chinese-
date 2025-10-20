package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B2_HarpeMediumEquipment extends Card {

    public SIGNI_B2_HarpeMediumEquipment()
    {
        setImageSets("WX24-P1-070");

        setOriginalName("中装　ハルパー");
        setAltNames("チュウソウハルパー Chuusou Harupaa");
        setDescription("jp",
                "@U $T1:あなたの他の＜アーム＞のシグニがバトルによってシグニ１体をバニッシュしたとき、対戦相手は手札を１枚捨てる。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。対戦相手は手札を１枚捨てる。"
        );

        setName("en", "Harpe, Medium Equipment");
        setDescription("en",
                "@U $T1: When 1 of your other <<Arm>> SIGNI banishes a SIGNI in battle, your opponent discards 1 card from their hand." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "中装 赫帕尔");
        setDescription("zh_simplified", 
                "@U $T1 :当你的其他的<<アーム>>精灵因为战斗把精灵1只破坏时，对战对手把手牌1张舍弃。" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。对战对手把手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceAbility() == null && !isOwnCard(caller) && isOwnCard(getEvent().getSourceCardIndex()) &&
                   getEvent().getSourceCardIndex().getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.ARM) &&
                   getEvent().getSourceCardIndex() != getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            discard(getOpponent(), 1);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            discard(getOpponent(), 1);
        }
    }
}
