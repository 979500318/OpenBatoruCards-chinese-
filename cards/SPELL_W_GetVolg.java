package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;

public final class SPELL_W_GetVolg extends Card {
    
    public SPELL_W_GetVolg()
    {
        setImageSets("WXDi-P05-052");
        setLinkedImageSets("WXDi-P05-032");
        
        setOriginalName("ゲット・ヴォルグ");
        setAltNames("ゲットヴォルグ Getto Vorugu");
        setDescription("jp",
                "以下の２つを行う。\n" +
                "$$1あなたのデッキの上からカードを５枚を見る。その中から白のカード１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。\n" +
                "$$2ターン終了時まで、あなたのセンタールリグは@>@U：このルリグがアタックしたとき、あなたの場に《大装　ゲイヴォルグ》がある場合、対戦相手のシグニ１体を対象とし、それをトラッシュに置く。@@を得る。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。"
        );
        
        setName("en", "Get Bolg");
        setDescription("en",
                "Perform the following.\n" +
                "$$1 Look at the top five cards of your deck. Reveal a white SIGNI from among them and add it to your hand. Put the rest on the bottom of your deck in any order.\n" +
                "$$2 Your Center LRIG gains@>@U: Whenever this LRIG attacks, if there is \"Gae Bolg, Full Armed\" on your field, put target SIGNI on your opponent's field into its owner's trash.@@until end of turn." +
                "~#Return target upped SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Get Volg");
        setDescription("en_fan",
                "@[@|Do the following 2:|@]@\n" +
                "$$1 Look at the top 5 cards of your deck. Reveal 1 white card from among them, add it to your hand, and put the rest on the bottom of your deck in any order.\n" +
                "$$2 Until end of turn, your center LRIG gains:" +
                "@>@U: Whenever this LRIG attacks, if there is a \"Gaevolg, Great Equipment\" on your field, target 1 of your opponent's SIGNI, and put it into the trash.@@" +
                "~#Target 1 of your opponent's upped SIGNI, and return it to their hand."
        );
        
		setName("zh_simplified", "获得·死棘");
        setDescription("zh_simplified", 
                "进行以下的2种。\n" +
                "$$1 从你的牌组上面看5张牌。从中把白色的精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。\n" +
                "$$2 直到回合结束时为止，你的核心分身得到\n" +
                "@>@U :当这只分身攻击时，你的场上有《大装　ゲイヴォルグ》的场合，对战对手的精灵1只作为对象，将其放置到废弃区。@@" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerSpellAbility(this::onSpellEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEff()
        {
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().withColor(CardColor.WHITE).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
            
            
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachAbility(getLRIG(getOwner()), attachedAuto, ChronoDuration.turnEnd());
        }
        private void onAttachedAutoEff()
        {
            if(new TargetFilter().own().SIGNI().withName("大装　ゲイヴォルグ").getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
                getAbility().getSourceCardIndex().getIndexedInstance().trash(target);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
            addToHand(target);
        }
    }
}
