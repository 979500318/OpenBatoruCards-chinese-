package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_W3_NovaDestinyImperial extends Card {
    
    public LRIG_W3_NovaDestinyImperial()
    {
        setImageSets("WXDi-P05-009");
        
        setOriginalName("運鳴　ノヴァ");
        setAltNames("ディスティニーインペリアルノヴァ Desutinii Inperiaru Nova");
        setDescription("jp",
                "@U：このルリグがアタックしたとき、あなたのトラッシュからカード１枚を対象とし、このルリグの下からカード１枚をルリグトラッシュに置いてもよい。そうした場合、それをデッキの一番上に置く。\n" +
                "@E：あなたのデッキの上からカードを３枚見る。その中からカードを１枚までデッキの一番上に戻し、残りを好きな順番でデッキの一番下に置く。\n" +
                "@A $G1 %W0：あなたのトラッシュから白のシグニ１枚を対象とし、それをデッキの一番上に置く。このターン終了時、カードを２枚引く。"
        );
        
        setName("en", "Nova, Destiny Imperial");
        setDescription("en",
                "@U: Whenever this LRIG attacks, you may put a card underneath this LRIG into its owner's LRIG trash. If you do, put target card from your trash on top of your deck.\n" +
                "@E: Look at the top three cards of your deck. Put up to one card on top of your deck and the rest on the bottom of your deck in any order.\n" +
                "@A $G1 %W0: Put target white SIGNI from your trash on top of your deck. At the end of this turn, draw two cards."
        );
        
        setName("en_fan", "Nova, Destiny Imperial");
        setDescription("en_fan",
                "@U: Whenever this LRIG attacks, you may target 1 card from your trash and put 1 card from under this LRIG into your LRIG trash. If you do, return it to the top of your deck.\n" +
                "@E: Look at the top 3 cards of your deck. Return up to 1 card from among them to the top of your deck, and put the rest on the bottom of your deck in any order.\n" +
                "@A $G1 %W0: Target 1 white SIGNI from your trash, and return it to the top of your deck. At the end of this turn, draw 2 cards."
        );
        
		setName("zh_simplified", "运鸣 超");
        setDescription("zh_simplified", 
                "@U :当这只分身攻击时，从你的废弃区把1张牌作为对象，可以从这只分身的下面把1张牌放置到分身废弃区。这样做的场合，将其放置到牌组最上面。\n" +
                "@E :从你的牌组上面看3张牌。从中把牌1张最多返回牌组最上面，剩下的任意顺序放置到牌组最下面。\n" +
                "@A $G1 %W0:从你的废弃区把白色的精灵1张作为对象，将其放置到牌组最上面。这个回合结束时，抽2张牌。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.NOVA);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.TOP).own().fromTrash()).get();
            
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TRASH).own().under(getCardIndex())).get();
                
                if(trash(cardIndex))
                {
                    returnToDeck(target, DeckPosition.TOP);
                }
            }
        }
        
        private void onEnterEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
            returnToDeck(cardIndex, DeckPosition.TOP);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private void onActionEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().SIGNI().withColor(CardColor.WHITE).fromTrash()).get();
            returnToDeck(cardIndex, DeckPosition.TOP);
            
            callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                draw(2);
            });
        }
    }
}
