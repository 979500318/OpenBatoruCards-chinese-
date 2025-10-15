package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class SPELL_B_EXTRA extends Card {

    public SPELL_B_EXTRA()
    {
        setImageSets("WXDi-P12-075");

        setOriginalName("EXTRA");
        setAltNames("エキストラ Ekusutora");
        setDescription("jp",
                "このターンにあなたが#Sではないスペルを使用していた場合、このスペルは使用できない。\n\n" +
                "カードを１枚引く。対戦相手は手札を１枚捨てる。このターン、あなたは#Sではないスペルを使用できない。" +
                "~#：対戦相手のルリグかシグニ１体を対象とする。このターン、それがアタックしたとき、対戦相手が手札を３枚捨てないかぎり、そのアタックを無効にする。"
        );

        setName("en", "EXTRA");
        setDescription("en",
                "If you have used a spell that isn't #S this turn, you cannot use this spell.\n\nDraw a card. Your opponent discards a card. You cannot use a spell that isn't #S this turn." +
                "~#Whenever target LRIG or SIGNI on your opponent's field attacks this turn, negate that attack unless your opponent discards three cards."
        );
        
        setName("en_fan", "EXTRA");
        setDescription("en_fan",
                "If you used a non-#S @[Dissona]@ spell this turn, you can't use this spell.\n\n" +
                "Draw 1 card. Your opponent discards 1 card from their hand.\nThis turn, you can't use non-#S @[Dissona]@ spells." +
                "~#Target 1 of your opponent's LRIG or SIGNI. This turn, whenever it attacks, disable that attack unless your opponent discards 3 cards from their hand."
        );
        
		setName("zh_simplified", "EXTRA");
        setDescription("zh_simplified", 
                "这个回合你把不是#S的魔法使用过的场合，不能把这张魔法使用。\n" +
                "抽1张牌。对战对手把手牌1张舍弃。这个回合，你不能把不是#S的魔法使用。" +
                "~#对战对手的分身或精灵1只作为对象。这个回合，当其攻击时，如果对战对手不把手牌3张舍弃，那么那次攻击无效。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            SpellAbility spell = registerSpellAbility(this::onSpellEff);
            spell.setCondition(this::onSpellEffCond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onSpellEffCond()
        {
            return GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_SPELL && isOwnCard(event.getCaller()) && 
                    !event.getCaller().isState(CardStateFlag.IS_DISSONA) && event.getCaller().getSourceCardIndex().getLocation() != CardLocation.CHECK_ZONE) > 0 ? ConditionState.BAD : ConditionState.OK;
        }
        private void onSpellEff()
        {
            draw(1);
            discard(getOpponent(), 1);
            
            addPlayerRuleCheck(PlayerRuleCheckType.CAN_USE_SPELL, getOwner(), ChronoDuration.turnEnd(), data -> 
                !data.getSourceCardIndex().getIndexedInstance().isState(CardStateFlag.IS_DISSONA) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE
            );
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().fromField()).get();
            
            if(target != null)
            {
                ChronoRecord record = new ChronoRecord(target, ChronoDuration.turnEnd());
                GFX.attachToChronoRecord(record, new GFXCardTextureLayer(target, new GFXTextureCardCanvas("border/discard", 0.75,3)));
                addCardRuleCheck(CardRuleCheckType.COST_TO_LAND_ATTACK, target, record, data -> new DiscardCost(0,3, ChoiceLogic.BOOLEAN));
            }
        }
    }
}
