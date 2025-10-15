package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_G3_CodeLabyrintQueen extends Card {
    
    public SIGNI_G3_CodeLabyrintQueen()
    {
        setImageSets("WXDi-P01-041");
        
        setOriginalName("コードラビリント　クイン");
        setAltNames("コードラビリントクイン Koodo Rabirinto Kuin");
        setDescription("jp",
                "@U：各アタックフェイズ開始時、対戦相手のシグニ２体を対象とし、それらの場所を入れ替える。\n" +
                "@U：対戦相手の場にあるシグニが１体以上他のシグニゾーンに移動したとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。" +
                "~#：@[@|どちらか１つを選ぶ。|@]@\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2カードを１枚引く。"
        );
        
        setName("en", "Quin, Code: Labyrinth");
        setDescription("en",
                "@U: At the beginning of each attack phase, swap the positions of two target SIGNI on your opponent's field.\n" +
                "@U: Whenever one or more SIGNI on your opponent's field moves into a different SIGNI Zone, target SIGNI on your opponent's field gets --2000 power until end of turn." +
                "~#Choose one --\n" +
                "$$1 Vanish target upped SIGNI on your opponent's field.\n" +
                "$$2 Draw a card."
        );
        
        setName("en_fan", "Code Labyrint Queen");
        setDescription("en_fan",
                "@U: At the beginning of each attack phase, target 2 of your opponent's SIGNI, and exchange their positions.\n" +
                "@U: Whenever 1 or more SIGNI on your opponent's field are moved to another SIGNI zone, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 Draw 1 card."
        );
        
		setName("zh_simplified", "迷狱代号 皇后");
        setDescription("zh_simplified", 
                "@U :各攻击阶段开始时，对战对手的精灵2只作为对象，将这些的场所交换。\n" +
                "@U :当对战对手的场上的精灵有1只以上往其他的精灵区移动时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(3);
        setPower(10000);
        
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
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.MOVE, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(2, new TargetFilter(TargetHint.MOVE).OP().SIGNI());
            if(data.get() != null)
            {
                exchange(data.get(0), data.get(1));
            }
        }
        
        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().isAtOnce(1) &&
                   CardLocation.isSIGNI(caller.getLocation()) && CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -2000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                draw(1);
            }
        }
    }
}
