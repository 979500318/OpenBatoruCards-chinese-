package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityHarmony;

public final class SIGNI_R3_CarnivalMemoriaNaturalStarPrincess extends Card {
    
    public SIGNI_R3_CarnivalMemoriaNaturalStarPrincess()
    {
        setImageSets("WXDi-P07-041", "WXDi-P07-041P", "SPDi02-15");
        setLinkedImageSets("WXDi-P07-TK01-A");
        
        setOriginalName("羅星姫　カーニバル//メモリア");
        setAltNames("ラセイキカーニバルメモリア Raseiki Kaanibaru Memoria");
        setDescription("jp",
                "@E %R #C #C #C：対戦相手のシグニ１体を対象とし、ターン終了時まで、それを《サーバント ＺＥＲＯ》にする。\n" +
                "@A $T1 %R0：==アイコンを持たないシグニ１体を対象とする。このシグニに付いているすべてのカードと、下に置かれているすべてのカードをトラッシュに置く。そうした場合、ターン終了時まで、このシグニはそれと同じカードになる。"
        );
        
        setName("en", "Carnival//Memoria, Galactic Queen");
        setDescription("en",
                "@E %R #C #C #C: Make target SIGNI on your opponent's field \"Servant ZERO\" until end of turn. \n" +
                "@A $T1 %R0: Put all cards that are underneath or attached to this SIGNI into its owner's trash. This SIGNI becomes the same card as target SIGNI on the field without an == icon until end of turn. "
        );
        
        setName("en_fan", "Carnival//Memoria, Natural Star Princess");
        setDescription("en_fan",
                "@E %R #C #C #C: Target 1 of your opponent's SIGNI, and until end of turn, it becomes \"Servant ZERO\".\n" +
                "@A $T1 %R0: Target 1 SIGNI without ==. Put all cards attached to this SIGNI and all cards placed under this SIGNI into the trash. If you do, until end of turn, this SIGNI becomes the same card as that SIGNI."
        );
        
		setName("zh_simplified", "罗星姬 嘉年华//回忆");
        setDescription("zh_simplified", 
                "@E %R#C #C #C对战对手的精灵1只作为对象，直到回合结束时为止，其变为《サーバント:ZERO》。（《サーバント　ＺＥＲＯ》是等级1、<<精元>>、力量1000、无色且不持有能力的精灵）\n" +
                "@A $T1 %R0不持有==图标的精灵1只作为对象。这只精灵附加的全部的牌和，下面放置的全部的牌放置到废弃区。直到回合结束时为止，这只精灵变为与其相同的牌。（=R和=H含有==）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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
            
            registerEnterAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.RED, 1)),
                new CoinCost(3)
            ), this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRANSFORM).OP().SIGNI()).get();
            transform(target, getLinkedImageSets().get(0), ChronoDuration.turnEnd());
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.COPY).SIGNI().or(
                new TargetFilter().not(new TargetFilter().withUseCondition(UseCondition.RISE)).not(new TargetFilter().withUseCondition(UseCondition.RESONA)).not(new TargetFilter().withStockAbility(StockAbilityHarmony.class)),
                new TargetFilter().withoutAbilities())
            ).get();
            
            if(target != null)
            {
                DataTable<CardIndex> data = new TargetFilter().own().withUnderType(CardUnderCategory.ATTACHED.getFlags() | CardUnderCategory.UNDER.getFlags()).fromLocation(getCardIndex().getLocation()).getExportedData();
                
                if(data.isEmpty() || trash(data) == data.size())
                {
                    transform(getCardIndex(), target.getImageSet(), ChronoDuration.turnEnd());
                }
            }
        }
    }
}
