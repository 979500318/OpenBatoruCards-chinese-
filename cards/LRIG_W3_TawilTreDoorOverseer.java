package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class LRIG_W3_TawilTreDoorOverseer extends Card {

    public LRIG_W3_TawilTreDoorOverseer()
    {
        setImageSets("WXDi-P16-008", "WXDi-P16-008U");

        setOriginalName("扉の俯瞰者　タウィル＝トレ");
        setAltNames("トビラノフカンシャタウィルトレ Tobira no Fukansha Tauiru Tore");
        setDescription("jp",
                "@C：あなたの白のシグニのパワーを＋1000する。\n" +
                "@E:あなたのデッキの上からカードを４枚見る。その中からカードを１枚まで手札に加え、カードを１枚までトラッシュに置き、残りを好きな順番でデッキの一番下に置く。\n" +
                "@A $G1 %W0：対戦相手のシグニ１体を対象とし、あなたのトラッシュから白のカード７枚をデッキに加えてシャッフルする。そうした場合、それをトラッシュに置く。"
        );

        setName("en", "Tawil =Tre=, Gate Overseer");
        setDescription("en",
                "@C: White SIGNI on your field get +1000 power.\n@E: Look at the top four cards of your deck. Add up to one card from among them to your hand. Put up to one of the remaining cards into your trash, and put the rest on the bottom of your deck in any order.\n@A $G1 %W0: Shuffle seven white cards from your trash into your deck. If you do, put target SIGNI on your opponent's field into its owner's trash."
        );
        
        setName("en_fan", "Tawil-Tre, Door Overseer");
        setDescription("en_fan",
                "@C: All of your white SIGNI get +1000 power.\n" +
                "@E: Look at the top 4 cards of your deck. Add up to 1 card from among them to your hand, put up to 1 card from among them into the trash, and put the rest on the bottom of your deck in any order.\n" +
                "@A $G1 %W0: Target 1 of your opponent's SIGNI, and shuffle 7 white cards from your trash into your deck. If you do, put it into the trash."
        );

		setName("zh_simplified", "扉的俯瞰者 塔维尔=TRE");
        setDescription("zh_simplified", 
                "@C :你的白色的精灵的力量+1000。\n" +
                "@E :从你的牌组上面看4张牌。从中把牌1张最多加入手牌，牌1张最多放置到废弃区，剩下的任意顺序放置到牌组最下面。\n" +
                "@A $G1 %W0:对战对手的精灵1只作为对象，从你的废弃区把白色的牌7张加入牌组洗切。这样做的场合，将其放置到废弃区。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAWIL);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new TargetFilter().own().SIGNI().withColor(CardColor.WHITE), new PowerModifier(1000));

            registerEnterAbility(this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }

        private void onEnterEff()
        {
            look(4);
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            addToHand(target);
            
            target = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().fromLooked()).get();
            trash(target);
            
            while(getLookedCount() > 0)
            {
                target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(target, DeckPosition.BOTTOM);
            }
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
            
            if(target != null)
            {
                DataTable<CardIndex> data = playerTargetCard(7, new TargetFilter().own().withColor(CardColor.WHITE).fromTrash());
                if(returnToDeck(data, DeckPosition.TOP) == 7 && shuffleDeck())
                {
                    trash(target);
                }
            }
        }
    }
}
