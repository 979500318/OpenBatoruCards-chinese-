package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry;
import open.batoru.core.gameplay.rulechecks.card.RuleCheckCanBeMoved;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityMultiEner;

public final class SIGNI_X3_EternalMemoria extends Card {

    public SIGNI_X3_EternalMemoria()
    {
        setImageSets("WXDi-P07-050", "WXDi-P07-050P");

        setOriginalName("夢限//メモリア");
        setAltNames("ムゲンメモリア Mugen Memoria");
        setDescription("jp",
                "@C：[[マルチエナ]]\n" +
                "@C：このシグニは対戦相手の効果によって場から他の領域に移動しない。\n" +
                "@A @[エクシード４]@：他のすべてのシグニをトラッシュに置く。このターン、あなたは他のシグニを場に出せない。"
        );

        setName("en", "Mugen//Memoria");
        setDescription("en",
                "@C: [[Multi Ener]]\n" +
                "@C: This SIGNI cannot be moved from your field to other Zones by your opponent's effects.\n" +
                "@A @[Exceed 4]@: Put all other SIGNI into their owner's trash. You can only have this SIGNI on your field this turn."
        );
        
        setName("en_fan", "Eternal//Memoria");
        setDescription("en_fan",
                "@C: [[Multi Ener]]\n" +
                "@C: This SIGNI can't be moved from the field to another zone by your opponent's effects.\n" +
                "@A @[Exceed 4]@: Put all other SIGNI from the field into the trash. This turn, you can't put other SIGNI onto the field."
        );

		setName("zh_simplified", "梦限//回忆");
        setDescription("zh_simplified", 
                "@C :[[万花色]]\n" +
                "@C :这只精灵不会因为对战对手的效果从场上往其他的领域移动。\n" +
                "@A @[超越 4]@:其他的全部的精灵放置到废弃区。这个回合，你不能把其他的精灵出场。\n"
        );

        setType(CardType.SIGNI);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ORIGIN);
        setLevel(3);
        setLimit(7);
        setPower(Double.POSITIVE_INFINITY);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerStockAbility(new StockAbilityMultiEner());

            registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckRegistry.CardRuleCheckType.CAN_BE_MOVED, this::onConstEffModRuleCheck));

            registerActionAbility(new ExceedCost(4), this::onActionEff);
        }

        private RuleCheckState onConstEffModRuleCheck(RuleCheckData data)
        {
            return data.getSourceAbility() != null && !isOwnCard(data.getSourceCardIndex()) &&
                   !CardLocation.isSIGNI(RuleCheckCanBeMoved.getDataTargetLocation(data)) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }

        private void onActionEff()
        {
            trash(new TargetFilter().SIGNI().own().except(getCardIndex()).getExportedData());
            trash(new TargetFilter().SIGNI().OP().getExportedData());
            
            addPlayerRuleCheck(PlayerRuleCheckRegistry.PlayerRuleCheckType.CAN_NEWLY_PUT_SIGNI_ON_FIELD, getOwner(), ChronoDuration.turnEnd(), data -> RuleCheckState.BLOCK);
        }
    }
}
