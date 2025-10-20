package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_B2_WaterGunSecondPlay extends Card {

    public SIGNI_B2_WaterGunSecondPlay()
    {
        setImageSets("WX24-P2-078");

        setOriginalName("弍ノ遊　ミズデッポウ");
        setAltNames("ニノユウミズデッポウ Ni no Yuu Mizudeppou");
        setDescription("jp",
                "@U：アタックフェイズの間、このシグニが場を離れたとき、あなたの手札からレベル１の＜遊具＞のシグニ１枚をダウン状態で場に出してもよい。そのシグニの@E能力は発動しない。" +
                "~#対戦相手のシグニ１体を対象とし、それをダウンし凍結する。対戦相手は手札を１枚捨てる。"
        );

        setName("en", "Water Gun, Second Play");
        setDescription("en",
                "@U: During the attack phase, when this SIGNI leaves the field, you may put 1 level 1 <<Playground Equipment>> SIGNI from your hand onto the field downed. That SIGNI's @E abilities don't activate." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "贰之游 水枪");
        setDescription("zh_simplified", 
                "@U 攻击阶段期间，当这只精灵离场时，可以从你的手牌把等级1的<<遊具>>精灵1张以横置状态出场。那只精灵的@E能力不能发动。" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。对战对手把手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.MOVE, this::onAutoEff1);
            auto1.setCondition(this::onAutoEffCond1);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond1()
        {
            return GamePhase.isAttackPhase(getCurrentPhase()) && !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1()
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT).withLevel(1).fromHand().playable()).get();
            putOnField(target, Enter.DOWNED | Enter.DONT_ACTIVATE);
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
