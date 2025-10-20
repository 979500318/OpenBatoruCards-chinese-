package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_B3_CaptainHookAzureDevil extends Card {
    
    public SIGNI_B3_CaptainHookAzureDevil()
    {
        setImageSets("WXDi-D05-015");
        
        setOriginalName("蒼魔　キャプテン・フック");
        setAltNames("ソウマキャプテンフック Souma Kyaputen Fukku");
        setDescription("jp",
                "@C：あなたのトラッシュにカードが２０枚以上あるかぎり、このシグニは対戦相手の効果によってバニッシュされない。\n" +
                "@U：アタックフェイズの間、このシグニが場を離れたとき、あなたのトラッシュから#Gを持たないシグニ５枚を対象とし、それらをデッキに加えてシャッフルする。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );
        
        setName("en", "Captain Hook, Azure Evil");
        setDescription("en",
                "@C: This SIGNI cannot be vanished by your opponent's effects as long as you have twenty or more cards in your trash.\n" +
                "@U: When this SIGNI leaves the field during an attack phase, shuffle five target SIGNI without a #G from your trash into your deck." +
                "~#Down target SIGNI on your opponent's field and freeze it. Draw a card."
        );
        
        setName("en_fan", "Captain Hook, Azure Devil");
        setDescription("en_fan",
                "@C: As long as there are 20 or more cards in your trash, this SIGNI can't be banished by your opponent's effects.\n" +
                "@U: During the attack phase, when this SIGNI leaves the field, target 5 SIGNI without #G @[Guard]@ from your trash, and shuffle them into your deck." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );
        
		setName("zh_simplified", "苍魔 船长·霍克");
        setDescription("zh_simplified", 
                "@C :你的废弃区的牌在20张以上时，这只精灵不会因为对战对手的效果破坏。\n" +
                "@U 攻击阶段期间，当这只精灵离场时，从你的废弃区把不持有#G的精灵5张作为对象，将这些加入牌组洗切。" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_BANISHED, this::onConstEffModRuleCheck));
            
            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return getTrashCount(getOwner()) >= 20 ? ConditionState.OK : ConditionState.BAD;
        }
        private RuleCheckState onConstEffModRuleCheck(RuleCheckData data)
        {
            return data.getSourceAbility() != null && !isOwnCard(data.getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
        
        private ConditionState onAutoEffCond()
        {
            return GamePhase.isAttackPhase(getCurrentPhase()) && !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            DataTable<CardIndex> data = playerTargetCard(5, new TargetFilter(TargetHint.SHUFFLE).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash());
            
            if(data.get() != null)
            {
                returnToDeck(data, DeckPosition.TOP);
                
                shuffleDeck();
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}
