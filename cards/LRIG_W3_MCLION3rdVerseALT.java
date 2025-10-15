package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_W3_MCLION3rdVerseALT extends Card {
    
    public LRIG_W3_MCLION3rdVerseALT()
    {
        setImageSets("WXDi-P04-008");
        
        setOriginalName("MC.LION　3rdVerse-ALT");
        setAltNames("エムシーリオンサードウァ゛ースアルト Emu Shii Rion Saado Vaasu Aruto");
        setDescription("jp",
                "@U $T1：このルリグがアタックしたとき、あなたのデッキをシャッフルし一番上を公開する。それがレベル１のシグニの場合、カードを１枚引く。それが#Gを持つシグニの場合、対戦相手のライフクロス１枚をクラッシュする。\n" +
                "@A $G1 %W0：対戦相手のシグニ１体を対象とし、それを手札に戻す。"
        );
        
        setName("en", "MC LION - 3rd Verse - ALT");
        setDescription("en",
                "@U $T1: When this LRIG attacks, shuffle your deck and reveal the top card of your deck. If that card is a level one SIGNI, draw a card. If that card is a SIGNI with a #G, crush one of your opponent's Life Cloth.\n" +
                "@A $G1 %W0: Return target SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "MC.LION 3rd Verse-ALT");
        setDescription("en_fan",
                "@U $T1: When this LRIG attacks, shuffle your deck and reveal the top card. If it is a level 1 SIGNI, draw 1 card. If it is a #G @[Guard]@ SIGNI, crush 1 of your opponent's life cloth.\n" +
                "@A $G1 %W0: Target 1 of your opponent's SIGNI, and return it to their hand."
        );
        
		setName("zh_simplified", "MC.LION 3rd Verse-ALT");
        setDescription("zh_simplified", 
                "@U $T1 当这只分身攻击时，你的牌组洗切把最上面公开。其是等级1的精灵的场合，抽1张牌。其是持有#G的精灵的场合，对战对手的生命护甲1张击溃。\n" +
                "@A $G1 %W0:对战对手的精灵1只作为对象，将其返回手牌。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LION);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private void onAutoEff()
        {
            shuffleDeck();
            
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                if(cardIndex.getIndexedInstance().getLevelByRef() != 1 ||
                   draw(1).get() == null)
                {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
                
                if(cardIndex.getIndexedInstance().isState(CardStateFlag.CAN_GUARD))
                {
                    crush(getOpponent());
                }
            }
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            addToHand(target);
        }
    }
}
