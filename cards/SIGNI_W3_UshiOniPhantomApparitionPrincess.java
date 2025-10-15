package open.batoru.data.cards;

import open.batoru.catalog.description.DescriptionParser;
import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public final class SIGNI_W3_UshiOniPhantomApparitionPrincess extends Card {

    public SIGNI_W3_UshiOniPhantomApparitionPrincess()
    {
        setImageSets("WX24-P4-044");

        setOriginalName("幻怪姫　ギュウキ");
        setAltNames("ゲンカイキギュウキ Genkaiki Gyuuki");
        setDescription("jp",
                "@C $TP：[[シャドウ（{{あなたのルリグトラッシュにあるアーツが持つ色$%1}}）]]\n" +
                "@C $TP：あなたのシグニのパワーをあなたのルリグトラッシュにあるアーツ１枚につき＋1000する。\n" +
                "@U：あなたのターン終了時、あなたのトラッシュから#Gを持つシグニ１枚を対象とし、%X %Xを支払ってもよい。そうした場合、それを手札に加える。"
        );

        setName("en", "Ushi Oni, Phantom Apparition Princess");
        setDescription("en",
                "@C $TP: [[Shadow ({{all colors among ARTS in your LRIG trash$%1}})]].\n" +
                "@C $TP: All of your SIGNI get +1000 power for each ARTS in your LRIG trash.\n" +
                "@U: At the end of your turn, target 1 SIGNI with #G @[Guard]@ from your trash, and you may pay %X %X. If you do, add it to your hand."
        );

		setName("zh_simplified", "幻怪姬 牛鬼");
        setDescription("zh_simplified", 
                "@C $TP :[[暗影（你的分身废弃区的必杀的持有颜色）]]\n" +
                "@C $TP :你的精灵的力量依据你的分身废弃区的必杀的数量，每有1张就+1000。\n" +
                "@U 你的回合结束时，从你的废弃区把持有#G的精灵1张作为对象，可以支付%X %X。这样做的场合，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.APPARITION);
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

            ConstantAbility cont1 = registerConstantAbility(new AbilityGainModifier(this::onConstEff1ModGetSample));
            cont1.setCondition(this::onConstEff1Cond);
            
            ConstantAbility cont2 = registerConstantAbility(new TargetFilter().own().SIGNI(), new PowerModifier(this::onConstEff2ModGetValue));
            cont2.setCondition(this::onConstEff2Cond);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onConstEff1Cond()
        {
            return !isOwnTurn() && new TargetFilter().own().ARTS().fromTrash(DeckType.LRIG).getExportedData().stream().anyMatch(c -> ((CardIndex)c).getIndexedInstance().getColor().getPrimaryValue() != CardColor.COLORLESS) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEff1ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond, this::onAttachedStockEffDynamicDesc));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getIndexedInstance().getColor().matches(getARTSColors()) ? ConditionState.OK : ConditionState.BAD;
        }
        private String onAttachedStockEffDynamicDesc()
        {
            StringJoiner result = new StringJoiner(DescriptionParser.formatSeparator());
            for(CardColor color : getARTSColors()) result.add(color.getLabel());
            return result.toString();
        }
        private Set<CardColor> getARTSColors()
        {
            return new TargetFilter().own().ARTS().fromTrash(DeckType.LRIG).getExportedData().stream().
                    flatMap(c -> ((CardIndex)c).getIndexedInstance().getColor().getValue().stream()).collect(Collectors.toSet());
        }
        
        private ConditionState onConstEff2Cond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private double onConstEff2ModGetValue(CardIndex cardIndex)
        {
            return 1000 * new TargetFilter().own().ARTS().fromTrash(DeckType.LRIG).getValidTargetsCount();
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            
            if(target != null && payEner(Cost.colorless(2)))
            {
                addToHand(target);
            }
        }
    }
}
