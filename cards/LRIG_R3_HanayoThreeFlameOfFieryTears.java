package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.CrushCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityAssassin;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;

public final class LRIG_R3_HanayoThreeFlameOfFieryTears extends Card {

    public LRIG_R3_HanayoThreeFlameOfFieryTears()
    {
        setImageSets("WXDi-P10-006", "WXDi-P10-006U");

        setOriginalName("火露炎　花代・参");
        setAltNames("ヒロウエンハナヨサン Hirouen Hanayo San");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A $T1 @[ライフクロス１枚をクラッシュする]@：カードを２枚引く。\n" +
                "@A $G1 %R0：あなたのデッキをシャッフルし一番上のカードを公開し手札に加える。その後、あなたのシグニ１体を対象とし、この方法で公開されたカードが##を持つ場合、ターン終了時まで、それは[[アサシン]]を得る。公開されたカードが##を持たない場合、ターン終了時まで、それは[[ダブルクラッシュ]]を得る。"
        );

        setName("en", "Hanayo Three, Blazing Banquet");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field.\n" +
                "@A $T1 @[Crush one of your Life Cloth]@: Draw two cards.\n" +
                "@A $G1 %R0: Shuffle your deck, reveal the top card, and add it to your hand. Then, if the card revealed this way has ##, target SIGNI on your field gains [[Assassin]] until end of turn. If the revealed card does not have ##, target SIGNI on your field gains [[Double Crush]] until end of turn."
        );
        
        setName("en_fan", "Hanayo Three, Flame of Fiery Tears");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and banish it.\n" +
                "@A $T1 @[Crush 1 of your life cloth]@: Draw 2 cards.\n" +
                "@A $G1 %R0: Shuffle your deck, reveal the top card of it, and add it to your hand. Then, if the card revealed this way had ## @[Life Burst]@, target 1 of your SIGNI, and until end of turn, it gains [[Assassin]]. If the revealed card did not have ## @[Life Burst]@, target 1 of your SIGNI, and until end of turn, it gains [[Double Crush]]."
        );

		setName("zh_simplified", "火露炎 花代·叁");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其破坏。\n" +
                "@A $T1 生命护甲1张击溃:抽2张牌。\n" +
                "@A $G1 %R0你的牌组洗切把最上面的牌公开加入手牌。然后，你的精灵1只作为对象，这个方法公开的牌持有##的场合，直到回合结束时为止，其得到[[暗杀]]。公开的牌不持有##的场合，直到回合结束时为止，其得到[[双重击溃]]。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HANAYO);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setLevel(3);
        setLimit(6);

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

            ActionAbility act1 = registerActionAbility(new CrushCost(1), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }

        private void onActionEff1()
        {
            draw(2);
        }

        private void onActionEff2()
        {
            shuffleDeck();
            
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                boolean hasLB = cardIndex.getIndexedInstance().findLifeBurstAbility().isPresent();

                addToHand(cardIndex);
                
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
                if(target != null) attachAbility(target, hasLB ? new StockAbilityAssassin() : new StockAbilityDoubleCrush(), ChronoDuration.turnEnd());
            }
        }
    }
}
