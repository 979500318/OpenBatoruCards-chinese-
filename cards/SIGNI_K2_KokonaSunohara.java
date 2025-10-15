package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K2_KokonaSunohara extends Card {

    public SIGNI_K2_KokonaSunohara()
    {
        setImageSets("WX25-CP1-091");

        setOriginalName("春原ココナ");
        setAltNames("スノハラココナ Sunohara Kokona");
        setDescription("jp",
                "@U：あなたの＜ブルアカ＞のシグニ１体がアタックしたとき、次の対戦相手のターン終了時まで、そのシグニのパワーを＋2000する。あなたのトラッシュからカードを１枚まで対象とし、それをこのシグニの下に置く。\n" +
                "@U：あなたのターン終了時、このシグニの下から＜ブルアカ＞のカードを３枚トラッシュに置いてもよい。そうした場合、【エナチャージ１】をする。" +
                "~{{U：あなたのアタックフェイズ開始時、対戦相手のデッキの上からカードを２枚トラッシュに置く。"
        );

        setName("en", "Sunohara Kokona");

        setName("en_fan", "Kokona Sunohara");
        setDescription("en",
                "@U: Whenever 1 of your <<Blue Archive>> SIGNI attacks, until the end of your opponent's next turn, that SIGNI gets +2000 power. Target up to 1 card from your trash, and put it under this SIGNI.\n" +
                "@U: At the end of your turn, you may put 3 <<Blue Archive>> cards from under this SIGNI into the trash. If you do, [[Ener Charge 1]]." +
                "~{{U: At the beginning of your attack phase, put the top 2 cards of your opponent's deck into the trash."
        );

		setName("zh_simplified", "春原心奈");
        setDescription("zh_simplified", 
                "@U :当你的<<ブルアカ>>精灵1只攻击时，直到下一个对战对手的回合结束时为止，那只精灵的力量+2000。从你的废弃区把牌1张最多作为对象，将其放置到这只精灵的下面。\n" +
                "@U :你的回合结束时，可以从这只精灵的下面把<<ブルアカ>>牌3张放置到废弃区。这样做的场合，[[能量填充1]]。\n" +
                "~{{U:你的攻击阶段开始时，从对战对手的牌组上面把2张牌放置到废弃区。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);

            AutoAbility auto3 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff3);
            auto3.setCondition(this::onAutoEff3Cond);
            auto3.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            gainPower(caller, 2000, ChronoDuration.nextTurnEnd(getOpponent()));
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.UNDER).own().fromTrash()).get();
            attach(getCardIndex(), target, CardUnderType.UNDER_GENERIC);
        }

        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).under(getCardIndex()));
            
            if(trash(data) == 3)
            {
                enerCharge(1);
            }
        }
        
        private ConditionState onAutoEff3Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff3(CardIndex caller)
        {
            millDeck(getOpponent(), 2);
        }
    }
}
