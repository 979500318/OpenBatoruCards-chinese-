package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.*;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_W3_CodeLabyrinthConnectDissona extends Card {

    public SIGNI_W3_CodeLabyrinthConnectDissona()
    {
        setImageSets("WXDi-P12-045");
        setLinkedImageSets("WXDi-P12-006");

        setOriginalName("コードラビリンス　コネクト//ディソナ");
        setAltNames("コードラビリンスコネクトディソナ Koodo Rabirinsu Konnekuto Disona");
        setDescription("jp",
                "@U：各アタックフェイズ開始時、対戦相手のシグニ１体を対象とし、対戦相手が%Xを支払わないかぎり、ターン終了時まで、それは能力を失う。\n" +
                "@U：あなたのターン終了時、あなたの場に《未開の巫女　ユキ》がいる場合、対戦相手のシグニ１体を対象とし、それをデッキの一番下に置く。\n" +
                "@E：あなたの他の#Sのシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋3000する。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをトラッシュに置く。"
        );

        setName("en", "Connect//Dissona, Code: Labyrinth");
        setDescription("en",
                "@U: At the beginning of each attack phase, target SIGNI on your opponent's field loses its abilities until end of turn unless your opponent pays %X.\n" +
                "@U: At the end of your turn, if \"Yuki, Savage Miko\" is on your field, put target SIGNI on your opponent's field on the bottom of its owner's deck.\n" +
                "@E: Another target #S SIGNI on your field gets +3000 power until the end of your opponent's next end phase." +
                "~#Put target upped SIGNI on your opponent's field into its owner's trash."
        );
        
        setName("en_fan", "Code Labyrinth Connect//Dissona");
        setDescription("en_fan",
                "@U: At the beginning of each attack phase, target 1 of your opponent's SIGNI, and until end of turn, it loses its abilities unless your opponent pays %X.\n" +
                "@U: At the end of your turn, if there is \"Yuki, Unbloomed Miko\" on your field, target 1 of your opponent's SIGNI, and put it on the bottom of their deck.\n" +
                "@E: Target 1 of your other #S @[Dissona]@ SIGNI, and until the end of your opponent's next turn, it gets +3000 power." +
                "~#Target 1 of your opponent's upped SIGNI, and put it into the trash."
        );

		setName("zh_simplified", "迷牢代号 灵犀//失调");
        setDescription("zh_simplified", 
                "@U 各攻击阶段开始时，对战对手的精灵1只作为对象，如果对战对手不把%X:支付，那么直到回合结束时为止，其的能力失去。\n" +
                "@U :你的回合结束时，你的场上有《未開の巫女　ユキ》的场合，对战对手的精灵1只作为对象，将其放置到牌组最下面。\n" +
                "@E 你的其他的#S的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+3000。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            
            registerEnterAbility(this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MUTE).OP().SIGNI()).get();
            
            if(target != null && !payEner(getOpponent(), Cost.colorless(1)))
            {
                disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.turnEnd());
            }
        }

        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("未開の巫女　ユキ"))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
                returnToDeck(target, DeckPosition.BOTTOM);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().dissona().except(getCardIndex())).get();
            gainPower(target, 3000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().upped()).get();
            trash(target);
        }
    }
}
