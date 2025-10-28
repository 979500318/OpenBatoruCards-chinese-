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
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class ARTS_K_GraveAwake extends Card {

    public ARTS_K_GraveAwake()
    {
        setImageSets("WX09-006", "WDK04-009");

        setOriginalName("グレイブ・アウェイク");
        setAltNames("グレイブアウェイク Gureibu Aueiku");
        setDescription("jp",
                "対戦相手のターンの間、このアーツの使用コストは%K %K %Kになる。\n\n" +
                "あなたのトラッシュからシグニ１枚を対象とし、それを場に出す。"
        );

        setName("en", "Grave Awake");
        setDescription("en",
                "During your opponent's turn, the cost for using this ARTS becomes %K %K %K.\n\n" +
                "Target 1 SIGNI from your trash, and put it onto the field."
        );

        setName("es", "Grave Awake");
        setDescription("es",
                "Durante el turno oponente, el costo de este ARTS se vuelve %K %K %K.\n\n" +
                "Selecciona 1 SIGNI de tu basura y ponla en el campo."
        );

        setName("zh_simplified", "墓地·苏醒");
        setDescription("zh_simplified", 
                "对战对手的回合期间，这张必杀的使用费用变为%K %K %K。\n" +
                "从你的废弃区把精灵1张作为对象，将其出场。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1));

        setPlayFormat(PlayFormat.KEY);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setCostModifier(this::onARTSEffModCostGetSample);
        }
        
        private CostModifier onARTSEffModCostGetSample()
        {
            return !isOwnTurn() ? new CostModifier(() -> new EnerCost(Cost.color(CardColor.BLACK, 3)), ModifierMode.SET) : null;
        }
        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().playable()).get();
            putOnField(target);
        }
    }
}
