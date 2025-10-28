package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class ARTS_G_ClearedOfAllCharges extends Card {
    
    public ARTS_G_ClearedOfAllCharges()
    {
        setImageSets("WDK03-009");
        
        setOriginalName("青天白日");
        setAltNames("フォールダウン Fooru Daun Fall Down");
        setDescription("jp",
                "@[ベット]@ － #C\n\n" +
                "あなたがベットする場合、このアーツの使用コストは%G0になる。\n\n" +
                "対戦相手のレベル４のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Cleared of All Charges");
        setDescription("en",
                "@[Bet]@ - #C\n\n" +
                "If you bet, the cost for using this ARTS becomes %G0.\n\n" +
                "Target 1 of your opponent's level 4 SIGNI, and banish it."
        );

        setName("es", "Libre de todos los cargos");
        setDescription("es",
                "@[Apuesta]@ - #C\n\n" +
                "Si apostaste, el costo de este ARTS se vuelve %G0.\n\n" +
                "Selecciona 1 SIGNI oponente de nivel 4 y desvánecela."
        );

        setName("zh_simplified", "青天白日");
        setDescription("zh_simplified", 
                "下注—#C\n" +
                "你下注的场合，这张必杀的使用费用变为%G0。\n" +
                "对战对手的等级4的精灵1只作为对象，将其破坏。"
        );
        
        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
        setPlayFormat(PlayFormat.KEY);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setBetCost(new CoinCost(1), Cost.color(CardColor.GREEN, 0), ModifierMode.SET);
        }
        
        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(4)).get();
            banish(target);
        }
    }
}
