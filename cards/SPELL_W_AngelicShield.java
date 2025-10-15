package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.AbilityORCost;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;

public final class SPELL_W_AngelicShield extends Card {

    public SPELL_W_AngelicShield()
    {
        setImageSets("WX25-P1-071");

        setOriginalName("エンジュリック・シールド");
        setAltNames("エンジュリックシールド Enjurikku Shiirudo");
        setDescription("jp",
                "あなたのセンターリルリグ１体を対象とし、次の対戦相手のターン終了時まで、それは@>@C：あなたが【ガード】する際、#Gを持つカード１枚捨てる代わりに、手札から＜天使＞のシグニ１枚捨てるかあなたのエナゾーンから＜天使＞のシグニ１枚をトラッシュに置いてもよい。@@を得る。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Angelic Shield");
        setDescription("en",
                "Target your center LRIG, and until the end of your opponent's next turn, it gains:" +
                "@>@C: You may [[Guard]] by discarding 1 <<Angel>> SIGNI from hand or putting 1 <<Angel>> SIGNI from your ener zone into the trash instead of discarding 1 card with #G @[Guard]@.@@" +
                "~#Target 1 of your opponent's upped SIGNI, and return it to their hand."
        );

		setName("zh_simplified", "天使·之盾");
        setDescription("zh_simplified", 
                "你的核心分身1只作为对象，直到下一个对战对手的回合结束时为止，其得到\n" +
                "@>@C 你[[防御]]时，把持有#G的牌1张舍弃，作为替代，可以从手牌把<<天使>>精灵1张舍弃或从你的能量区把<<天使>>精灵1张放置到废弃区。@@" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            spell = registerSpellAbility(this::onSpellEffPreTarget, this::onSpellEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().LRIG()));
        }
        private void onSpellEff()
        {
            ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.COST_TO_GUARD, TargetFilter.HINT_OWNER_OWN, data ->
                new AbilityORCost(AbilityORCost.REPLACE_DEFAULT, 
                    new DiscardCost(0,1, new TargetFilter().SIGNI().withClass(CardSIGNIClass.ANGEL)),
                    new TrashCost(0,1, new TargetFilter().SIGNI().withClass(CardSIGNIClass.ANGEL).fromEner())
                )
            ));
            attachAbility(spell.getTarget(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
            addToHand(target);
        }
    }
}

