package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class LRIGA_W2_AssistLizeLevel2IceWall extends Card {

    public LRIGA_W2_AssistLizeLevel2IceWall()
    {
        setImageSets("WXDi-CP01-013");

        setOriginalName("【アシスト】リゼ　レベル２【氷壁】");
        setAltNames("アシストリゼレベルニヒョウヘキ Ashisuto Rize Reberu Ni Hyouheki Tsurugi Assist Lize");
        setDescription("jp",
                "@E：対戦相手のシグニを２体まで対象とし、ターン終了時まで、それらは@>@C：アタックできない。@@を得る。\n" +
                "@E %X %X %X：あなたのデッキの上からカードを５枚見る。その中からシグニを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "[Assist] Lize, Level 2 [Ice Wall]");
        setDescription("en",
                "@E: Up to two target SIGNI on your opponent's field gain@>@C: This SIGNI cannot attack.@@until end of turn.\n@E %X %X %X: Look at the top five cards of your deck. Reveal up to two SIGNI from among them and add them to your hand. Put the rest on the bottom of your deck in any order.\n"
        );
        
        setName("en_fan", "[Assist] Lize Level 2 [Ice Wall]");
        setDescription("en_fan",
                "@E: Target up to 2 of your opponent's SIGNI, and until end of turn, they gain:" +
                "@>@C: Can't attack.@@" +
                "@E %X %X %X: Look at the top 5 cards of your deck. Reveal up to 2 of them, and add them to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "【支援】莉泽 等级2【冰壁】");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵2只最多作为对象，直到回合结束时为止，这些得到\n" +
                "@>@C :不能攻击。@@\n" +
                "@E %X %X %X:从你的牌组上面看5张牌。从中把精灵2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LIZE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(3));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(3)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).OP().SIGNI());
            if(data.get() != null) for(int i=0;i<data.size();i++) attachAbility(data.get(i), new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
        
        private void onEnterEff2()
        {
            look(5);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().fromLooked());
            reveal(data);
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
