package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B3_CodeEatCoffeeDissona extends Card {

    public SIGNI_B3_CodeEatCoffeeDissona()
    {
        setImageSets("WXDi-P13-074");

        setOriginalName("コードイート　コーヒー//ディソナ");
        setAltNames("コードイートコーヒーディソナ Koodo Iito Koohii Disona");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのアップ状態の#Sのシグニ２体をダウンしてもよい。そうした場合、カードを１枚引き、対戦相手は手札を１枚捨てる。その後、あなたは対戦相手のシグニ１体を対象とし、それを凍結する。"
        );

        setName("en", "Coffee//Dissona, Code: Eat");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may down two upped #S SIGNI on your field. If you do, draw a card and your opponent discards a card. Then, freeze target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Code Eat Coffee//Dissona");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, you may down 2 of your upped #S @[Dissona]@ SIGNI. If you do, draw 1 card, and your opponent discards 1 card from their hand. Then, target 1 of your opponent's SIGNI, and freeze it."
        );

		setName("zh_simplified", "食用代号 咖啡//失调");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，可以把你的竖直状态的#S的精灵2只横置。这样做的场合，抽1张牌，对战对手把手牌1张舍弃。然后，你把对战对手的精灵1只作为对象，将其冻结。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.COOKING);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.DOWN).SIGNI().dissona().upped());
            
            if(down(data) == 2)
            {
                draw(1);
                discard(getOpponent(), 1);
                
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
                freeze(target);
            }
        }
    }
}
