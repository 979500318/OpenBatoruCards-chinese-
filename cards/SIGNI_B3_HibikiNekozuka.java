package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.CardDataImageSet.Mask;

public final class SIGNI_B3_HibikiNekozuka extends Card {

    public SIGNI_B3_HibikiNekozuka()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_LEFT+"WXDi-CP02-055");

        setOriginalName("猫塚ヒビキ");
        setAltNames("ネコヅカヒビキ Nekozuka Hibiki");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるすべてのシグニが＜ブルアカ＞の場合、対戦相手は手札を１枚捨てる。\n" +
                "@U：このシグニがアタックしたとき、このターンにあなたが手札から＜ブルアカ＞のカードを１枚以上捨てていた場合、対戦相手は手札を１枚捨てる。" +
                "~{{E @[手札から＜ブルアカ＞のカードを２枚捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－10000する。"
        );

        setName("en", "Nekozuka Hibiki");
        setDescription("en",
                "@U: At the beginning of your attack phase, if all the SIGNI on your field are <<Blue Archive>>, your opponent discards a card.\n@U: Whenever this SIGNI attacks, if you have discarded one or more <<Blue Archive>> cards this turn, your opponent discards a card.~{{E @[Discard two <<Blue Archive>> cards]@: Target SIGNI on your opponent's field gets --10000 power until end of turn."
        );
        
        setName("en_fan", "Hibiki Nekozuka");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if all of your SIGNI are <<Blue Archive>> SIGNI, your opponent discards 1 card from their hand.\n" +
                "@U: Whenever this SIGNI attacks, if you discarded 1 or more cards from your hand this turn, your opponent discards 1 card from their hand." +
                "~{{E @[Discard 2 <<Blue Archive>> cards from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power."
        );

		setName("zh_simplified", "猫塚响");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的全部的精灵是<<ブルアカ>>的场合，对战对手把手牌1张舍弃。\n" +
                "@U :当这只精灵攻击时，这个回合你从手牌把<<ブルアカ>>牌1张以上舍弃过的场合，对战对手把手牌1张舍弃。\n" +
                "~{{E从手牌把<<ブルアカ>>牌2张舍弃:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);

            EnterAbility enter = registerEnterAbility(new DiscardCost(2, new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), this::onEnterEff);
            enter.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().not(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)).getValidTargetsCount() == 0)
            {
                discard(getOpponent(), 1);
            }
        }
        
        private void onAutoEff2()
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.DISCARD && isOwnCard(event.getCaller())) >= 1)
            {
                discard(getOpponent(), 1);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -10000, ChronoDuration.turnEnd());
        }
    }
}
