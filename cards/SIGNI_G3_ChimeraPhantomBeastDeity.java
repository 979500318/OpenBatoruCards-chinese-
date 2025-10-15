package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.stock.StockAbilityLancer;
import open.batoru.data.ability.stock.StockAbilitySLancer;

public final class SIGNI_G3_ChimeraPhantomBeastDeity extends Card {

    public SIGNI_G3_ChimeraPhantomBeastDeity()
    {
        setImageSets("WXDi-P11-047");

        setOriginalName("幻獣神　キメラ");
        setAltNames("ゲンジュウシンキメラ Genjuushin Kimera");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚見る。その中から＜地獣＞のシグニを２枚までこのシグニの下に置き、残りを好きな順番でデッキの一番下に置く。\n" +
                "@A @[このシグニの下からカード１枚をトラッシュに置く]@：あなたのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋3000する。\n" +
                "@A %G @[このシグニの下からカード１枚をトラッシュに置く]@：ターン終了時まで、このシグニは【ランサー】を得る。"
        );

        setName("en", "Chimera, Phantom Beast Deity");
        setDescription("en",
                "@E: Look at the top three cards of your deck. Put up to two <<Terra Beast>> SIGNI from among them under this SIGNI. Put the rest on the bottom of your deck in any order.\n" +
                "@A @[Put a card underneath this SIGNI into its owner's trash]@: Target SIGNI on your field gets +3000 power until end of turn.\n" +
                "@A %G @[Put a card underneath this SIGNI into its owner's trash]@: This SIGNI gains [[Lancer]] until end of turn."
        );
        
        setName("en_fan", "Chimera, Phantom Beast Deity");
        setDescription("en_fan",
                "@E: Look at the top 3 cards of your deck. Put up to 2 <<Earth Beast>> SIGNI from among them under this SIGNI, and put the rest on the bottom of your deck in any order.\n" +
                "@A @[Put 1 card from under this SIGNI into the trash]@: Target 1 of your SIGNI, and until end of turn, it gets +3000 power.\n" +
                "@A %G @[Put 1 card from under this SIGNI into the trash]@: Until end of turn, this SIGNI gains [[Lancer]]."
        );

		setName("zh_simplified", "幻兽神 凯美拉");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看3张牌。从中把<<地獣>>精灵2张最多放置到这只精灵的下面，剩下的任意顺序放置到牌组最下面。\n" +
                "@A 从这只精灵的下面把1张牌放置到废弃区:你的精灵1只作为对象，直到回合结束时为止，其的力量+3000。\n" +
                "@A %G从这只精灵的下面把1张牌放置到废弃区:直到回合结束时为止，这只精灵得到[[枪兵]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
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

            registerEnterAbility(this::onEnterEff);
            
            registerActionAbility(new TrashCost(new TargetFilter().under(cardId)), this::onActionEff1);

            ActionAbility act2 = registerActionAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.GREEN, 1)), new TrashCost(new TargetFilter().under(cardId))), this::onActionEff2);
            act2.setCondition(this::onActionEff2Cond);
        }

        private void onEnterEff()
        {
            look(3);

            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.UNDER).own().SIGNI().withClass(CardSIGNIClass.EARTH_BEAST).fromLooked());
            attach(getCardIndex(), data, CardUnderType.UNDER_GENERIC);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
            gainPower(target, 3000, ChronoDuration.turnEnd());
        }

        private ConditionState onActionEff2Cond()
        {
            return getCardIndex().getIndexedInstance().getAbilityList().stream().anyMatch(ability ->
                    ability.getSourceStockAbility() instanceof StockAbilityLancer ||
                    ability.getSourceStockAbility() instanceof StockAbilitySLancer) ? ConditionState.WARN : ConditionState.OK;
        }
        private void onActionEff2()
        {
            attachAbility(getCardIndex(), new StockAbilityLancer(), ChronoDuration.turnEnd());
        }
    }
}
