package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventTarget;

public final class SIGNI_B2_HopeDiaDissonaNaturalStone extends Card {

    public SIGNI_B2_HopeDiaDissonaNaturalStone()
    {
        setImageSets("WXDi-P12-074");

        setOriginalName("羅石　ホープダイヤ//ディソナ");
        setAltNames("ラセキホープダイヤディソナ Raseki Hoopu Daiya Disona");
        setDescription("jp",
                "@U $T1：対戦相手のターンの間、このシグニが対戦相手の、能力か効果の対象になったとき、対戦相手は手札を１枚捨てる。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。対戦相手は手札を１枚捨てる。"
        );

        setName("en", "Hope Dia//Dissona, Natural Crystal");
        setDescription("en",
                "@U $T1: During your opponent's turn, when this SIGNI becomes the target of an ability or effect of your opponent, your opponent discards a card." +
                "~#Down target SIGNI on your opponent's field and freeze it. Your opponent discards a card."
        );
        
        setName("en_fan", "Hope Dia//Dissona, Natural Stone");
        setDescription("en_fan",
                "@U $T1: During your opponent's turn, when this SIGNI is targeted by your opponent's ability or effect, your opponent discards 1 card from their hand." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "罗石 希望蓝钻//失调");
        setDescription("zh_simplified", 
                "@U $T1 :对战对手的回合期间，当这只精灵被作为对战对手的，能力或效果的对象时，对战对手把手牌1张舍弃。" +
                "~#对战对手的精灵1只作为对象，将其#D并冻结。对战对手把手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.TARGET, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return !isOwnTurn() && getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()) &&
                   EventTarget.getDataSourceTargetRole() != getCurrentOwner() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
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
