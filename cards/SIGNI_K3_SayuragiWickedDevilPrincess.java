package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K3_SayuragiWickedDevilPrincess extends Card {

    public SIGNI_K3_SayuragiWickedDevilPrincess()
    {
        setImageSets("WX24-P3-057");
        setLinkedImageSets("WX24-P3-030");

        setOriginalName("凶魔姫　サユラギ");
        setAltNames("キョウマキサユラギ Kyoumaki Sayuragi");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたか対戦相手のデッキの上からカードを３枚トラッシュに置く。\n" +
                "@U：このシグニがアタックしたとき、あなたの場に《黒想の花嫁　アルフォウ》がいる場合、対戦相手のシグニ１体を対象とし、%K %Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーをあなたのトラッシュにある＜悪魔＞のシグニ１枚につき－1000する。"
        );

        setName("en", "Sayuragi, Wicked Devil Princess");
        setDescription("en",
                "@U: At the beginning of your attack phase, put the top 3 cards of your or your opponent's deck into the trash.\n" +
                "@U: Whenever this SIGNI attacks, if your LRIG is \"Alfou, Bride of Black Thoughts\", target 1 of your opponent's SIGNI, and you may pay %K %X. If you do, until end of turn, it gets --1000 power for each <<Devil>> SIGNI in your trash."
        );

		setName("zh_simplified", "凶魔姬 小瑶");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从你或对战对手的牌组上面把3张牌放置到废弃区。\n" +
                "@U :当这只精灵攻击时，你的场上有《黒想の花嫁　アルフォウ》的场合，对战对手的精灵1只作为对象，可以支付%K%X。这样做的场合，直到回合结束时为止，其的力量依据你的废弃区的<<悪魔>>精灵的数量，每有1只就-1000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            millDeck(playerChoiceAction(ActionHint.OWN, ActionHint.OPPONENT) == 1 ? getOwner() : getOpponent(), 3);
        }
        
        private void onAutoEff2()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("黒想の花嫁　アルフォウ"))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                
                if(target != null && payEner(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1)))
                {
                    gainPower(target, -1000 * new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DEVIL).fromTrash().getValidTargetsCount(), ChronoDuration.turnEnd());
                }
            }
        }
    }
}
