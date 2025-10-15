package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityGuard;

public final class SIGNI_W2_Code2434KanaSukoya extends Card {

    public SIGNI_W2_Code2434KanaSukoya()
    {
        setImageSets("WXDi-CP01-038");

        setOriginalName("コード２４３４　健屋花那");
        setAltNames("コードニジサンジスコヤカナ Koodo Nijisanji Sukoya Kana");
        setDescription("jp",
                "@E：あなたの他の＜バーチャル＞のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それは@>@C：対戦相手のターンの間、このシグニは対戦相手のレベル２以下の、ルリグとシグニの効果によってバニッシュされない。@@を得る。" +
                "~#：カードを１枚引く。このターン、あなたの手札にあるシグニは#Gを得る。"
        );

        setName("en", "Sukoya Kana, Code 2434");
        setDescription("en",
                "@E: Another target <<Virtual>> SIGNI on your field gains@>@C: During your opponent's turn, this SIGNI cannot be vanished by the effects of your opponent's LRIG and SIGNI that are level two or less.@@until the end of your opponent's next end phase. " +
                "~#Draw a card. The SIGNI in your hand gain a #G this turn. "
        );
        
        setName("en_fan", "Code 2434 Kana Sukoya");
        setDescription("en_fan",
                "@E: Target 1 of your other <<Virtual>> SIGNI, and until the end of your opponent's next turn, it gains:" +
                "@>@C: During your opponent's turn, this SIGNI can't be banished by the effects of your opponent's level 2 or lower LRIG and SIGNI.@@" +
                "~#Draw 1 card. This turn, all SIGNI in your hand gain #G @[Guard]@."
        );

		setName("zh_simplified", "2434代号 健屋花那");
        setDescription("zh_simplified", 
                "@E :你的其他的<<バーチャル>>精灵1只作为对象，直到下一个对战对手的回合结束时为止，其得到\n" +
                "@>@C :对战对手的回合期间，这只精灵不会因为对战对手的等级2以下的，分身和精灵的效果破坏@@\n" +
                "。（与精灵战斗或力量在0以下的场合，会被破坏）" +
                "~#抽1张牌。这个回合，你的手牌的精灵得到#G。（持有#G的精灵得到[[防御]]）\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
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

            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).except(getCardIndex())).get();
            
            if(target != null)
            {
                ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_BANISHED, this::onAttachedConstEffModRuleCheck));
                attachedConst.setCondition(this::onAttachedConstEffCond);
                
                attachAbility(target, attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        private ConditionState onAttachedConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private RuleCheckState onAttachedConstEffModRuleCheck(RuleCheckData data)
        {
            return data.getSourceAbility() != null &&
                   (CardType.isLRIG(data.getSourceCardIndex().getCardReference().getType()) || CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType())) &&
                   data.getSourceCardIndex().getIndexedInstance().getLevel().getValue() <= 2 ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
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
