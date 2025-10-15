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
import open.batoru.data.ability.modifiers.RuleCheckModifier;

import java.util.List;

public final class SIGNI_G1_MerliVerdantBeauty extends Card {

    public SIGNI_G1_MerliVerdantBeauty()
    {
        setImageSets("WXDi-P03-074");

        setOriginalName("翠美　マーライ");
        setAltNames("スイビマーライ Suibi Maarai");
        setDescription("jp",
                "@C：このシグニは対戦相手のレベル１のシグニの効果によってバニッシュされない。\n" +
                "@E：あなたのルリグ１体を対象とし、ターン終了時まで、このシグニはそれが持つ色１つを得る。" +
                "~#：[[エナチャージ１]]をする。このターン、次にあなたがシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Marai, Jade Beauty");
        setDescription("en",
                "@C: This SIGNI cannot be vanished by the effects of your opponent's level one SIGNI.\n" +
                "@E: This SIGNI gains a color of target LRIG on your field until end of turn." +
                "~#[[Ener Charge 1]]. The next time you would take damage from a SIGNI this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Merli, Verdant Beauty");
        setDescription("en_fan",
                "@C: Can't be banished by the effects of your opponent's level 1 SIGNI.\n" +
                "@E: Target 1 of your LRIGs, and until end of turn, this SIGNI gains 1 of its colors." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );

		setName("zh_simplified", "翠美 鱼尾狮");
        setDescription("zh_simplified", 
                "@C :这只精灵不会因为对战对手的等级1的精灵的效果破坏。\n" +
                "@E :你的分身1只作为对象，直到回合结束时为止，这只精灵得到其持有颜色的1种。" +
                "~#[[能量填充1]]。这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_BANISHED, this::onConstEffModRuleCheck));

            registerEnterAbility(this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private RuleCheckState onConstEffModRuleCheck(RuleCheckData data)
        {
            return data.getSourceAbility() != null && !isOwnCard(data.getSourceCardIndex()) &&
                    CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType()) &&
                    data.getSourceCardIndex().getIndexedInstance().getLevel().getValue() == 1 ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.COPY).own().anyLRIG()).get();
            
            if(target != null && target.getIndexedInstance().getColor().getPrimaryValue() != CardColor.COLORLESS)
            {
                List<CardColor> listColors = target.getIndexedInstance().getColor().getValue();
                CardColor color = listColors.size() == 1 ? listColors.getFirst() : playerChoiceColor(listColors.toArray(CardColor[]::new));
                
                gainValue(getCardIndex(), getColor(),color, ChronoDuration.turnEnd());
            }
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
