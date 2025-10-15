package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_B3_KarinHououDenonbu extends Card {

    public SIGNI_B3_KarinHououDenonbu()
    {
        setImageSets("WXDi-P14-085", "WXDi-P14-085P");

        setOriginalName("電音部　鳳凰火凛");
        setAltNames("デンオンブホウオウカリン Denonbu Houou Karin");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、手札を３枚捨ててもよい。そうした場合、それをバニッシュする。この効果であなたが＜電音部＞のシグニを３枚捨てた場合、対戦相手は手札を１枚捨てる。\n" +
                "@E %X：カードを１枚引く。"
        );

        setName("en", "DEN-ON-BU Karin Houou");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may discard three cards. If you do, vanish target SIGNI on your opponent's field. If you discarded three <<DEN-ON-BU>> SIGNI with this effect, your opponent discards a card.\n@E %X: Draw a card."
        );
        
        setName("en_fan", "Karin Houou, Denonbu");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may discard 3 cards from your hand. If you do, banish it. If you discarded 3 <<Denonbu>> SIGNI with this effect, your opponent discards 1 card from their hand.\n" +
                "@E %X: Draw 1 card."
        );

		setName("zh_simplified", "电音部 凤凰火凛");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，可以把手牌3张舍弃。这样做的场合，将其破坏。这个效果你把<<電音部>>精灵3张舍弃的场合，对战对手把手牌1张舍弃。\n" +
                "@E %X:抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DENONBU);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null)
            {
                DataTable<CardIndex> data = discard(0,3, ChoiceLogic.BOOLEAN);
                
                if(data.size() == 3)
                {
                    banish(target);
                    
                    if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DENONBU).fromTrash().match(data).getValidTargetsCount() == 3)
                    {
                        discard(getOpponent(), 1);
                    }
                }
            }
        }
        
        private void onEnterEff()
        {
            draw(1);
        }
    }
}
