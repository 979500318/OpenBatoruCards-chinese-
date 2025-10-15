package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityGuard;

public final class SIGNI_W2_DigiLockMediumTrap extends Card {

    public SIGNI_W2_DigiLockMediumTrap()
    {
        setImageSets("WXDi-P15-085");

        setOriginalName("中罠　デジロック");
        setAltNames("チュウビンデジロック Chuubin Digirokku");
        setDescription("jp",
                "@C：対戦相手のターンの間、あなたの白のシグニは対戦相手の効果によって能力を失わない。\n" +
                "@A #D：次の対戦相手のターン終了時まで、このシグニのパワーを＋5000する。" +
                "~#：カードを１枚引く。このターン、あなたの手札にあるシグニは#Gを得る。"
        );

        setName("en", "Digi - Lock, Medium Trickster");
        setDescription("en",
                "@C: During your opponent's turn, white SIGNI on your field do not lose their abilities by your opponent's effects.\n@A #D: This SIGNI gets +5000 power until the end of your opponent's next end phase." +
                "~#Draw a card. The SIGNI in your hand gain a #G this turn. "
        );
        
        setName("en_fan", "Digi Lock, Medium Trap");
        setDescription("en_fan",
                "@C: During your opponent's turn, your white SIGNI can't lose their abilities due to your opponent's effects.\n" +
                "@A #D: Until the end of your opponent's next turn, this SIGNI gets +5000 power." +
                "~#Draw 1 card. This turn, all SIGNI in your hand gain #G @[Guard]@."
        );

		setName("zh_simplified", "中罠 电子锁");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，你的白色的精灵不会因为对战对手的效果把能力失去。\n" +
                "@A #D:直到下一个对战对手的回合结束时为止，这只精灵的力量+5000。" +
                "~#抽1张牌。这个回合，你的手牌的精灵得到#G。（持有#G的精灵得到[[防御]]）\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI().withColor(CardColor.WHITE),
                new RuleCheckModifier<>(CardRuleCheckType.CAN_ABILITY_BE_DISABLED, this::onConstEffModRuleCheck)
            );
            
            registerActionAbility(new DownCost(), this::onActionEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private RuleCheckState onConstEffModRuleCheck(RuleCheckData data)
        {
            return !isOwnCard(data.getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
        
        private void onActionEff()
        {
            gainPower(getCardIndex(), 5000, ChronoDuration.nextTurnEnd(getOpponent()));
        }

        private void onLifeBurstEff()
        {
            draw(1);
            
            ConstantAbilityShared attachedConstShared = new ConstantAbilityShared(new TargetFilter().own().SIGNI().fromHand(), new AbilityGainModifier(this::onAttachedConstEffModGetSample));
            attachPlayerAbility(getOwner(), attachedConstShared, ChronoDuration.turnEnd());
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityGuard());
        }
    }
}
