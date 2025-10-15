package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_W3_NovaSkyfeather extends Card {
    
    public LRIG_W3_NovaSkyfeather()
    {
        setImageSets("WXDi-P03-023", "SPDi07-08","SPDi08-08");
        
        setOriginalName("天翔　ノヴァ");
        setAltNames("スカイフェザーノヴァ Sukai Fezaa Nova");
        setDescription("jp",
                "=T ＜うちゅうのはじまり＞\n" +
                "^E：対戦相手のシグニ１体を対象とし、それを手札に戻す。\n" +
                "@U：このルリグがアタックしたとき、このルリグの下にカードが５枚以上ある場合、あなたのトラッシュから#Gを持つシグニを１枚まで対象とし、それをデッキの一番上に置く。７枚以上ある場合、追加でカードを１枚引く。\n" +
                "@A $G1 %W0：あなたの他のルリグの下にあるすべてのカードをこのルリグの下に置く。"
        );
        
        setName("en", "Nova, Skyfeather");
        setDescription("en",
                "=T <<UCHU NO HAJIMARI>>\n" +
                "^E: Return target SIGNI on your opponent's field to its owner's hand.\n" +
                "@U: Whenever this LRIG attacks, if there are five or more cards underneath it, put up to one target SIGNI with a #G from your trash on the top of your deck. If there are seven or more cards underneath it, draw a card.\n" +
                "@A $G1 %W0: Put all cards underneath all other LRIG on your field under this LRIG."
        );
        
        setName("en_fan", "Nova, Skyfeather");
        setDescription("en_fan",
                "=T <<Universe's Beginning>>\n" +
                "^E: Target 1 of your opponent's SIGNI, and return it to their hand.\n" +
                "@U: Whenever this LRIG attacks, if there are 5 or more cards under this LRIG, target up to 1 SIGNI with #G @[Guard]@ from your trash, and return it to the top of your deck. If there are 7 or more cards under this LRIG, additionally draw 1 card.\n" +
                "@A $G1 %W0: Put all of the cards under your other LRIGs under this LRIG."
        );
        
		setName("zh_simplified", "天翔 超");
        setDescription("zh_simplified", 
                "=T<<うちゅうのはじまり>>\n" +
                "^E:对战对手的精灵1只作为对象，将其返回手牌。\n" +
                "@U 当这只分身攻击时，这只分身的下面的牌在5张以上的场合，从你的废弃区把持有#G的精灵1张最多作为对象，将其放置到牌组最上面。7张以上的场合，追加抽1张牌。\n" +
                "@A $G1 %W0:你的其他的分身的下面的全部的牌放置到这只分身的下面。\n"
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
            
            EnterAbility enter = registerEnterAbility(this::onEnterEff);
            enter.setCondition(this::onEnterEffCond);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onEnterEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            addToHand(target);
        }
        
        private void onAutoEff()
        {
            int countUnder = new TargetFilter().own().under(getLRIG(getOwner())).getValidTargetsCount();
            
            if(countUnder >= 5)
            {
                CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.TOP).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
                returnToDeck(target, DeckPosition.TOP);
            }
            
            if(countUnder >= 7) draw(1);
        }
        
        private void onActionEff()
        {
            forEachLRIGOnField(getOwner(), cardIndex -> {
                if(cardIndex == getCardIndex()) return;
                
                forEachCardUnder(cardIndex, cardIndexUnder -> {
                    attach(getCardIndex(), cardIndexUnder, CardUnderType.UNDER_GENERIC);
                });
            });
        }
    }
}
