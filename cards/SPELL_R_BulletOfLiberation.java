package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SPELL_R_BulletOfLiberation extends Card {

    public SPELL_R_BulletOfLiberation()
    {
        setImageSets("WXDi-P15-063");

        setOriginalName("解放の弾丸");
        setAltNames("カイホウノダンガン Kaihou no Dangan");
        setDescription("jp",
                "下にカードがあるあなたの＜解放派＞のシグニ１体を対象とし、ターン終了時まで、それは【アサシン】を得る。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Liberating Shot");
        setDescription("en",
                "Target <<Liberation Division>> SIGNI on your field with a card underneath it gains [[Assassin]] until end of turn." +
                "~#Vanish target upped SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Bullet of Liberation");
        setDescription("en_fan",
                "Target 1 of your <<Liberation Faction>> SIGNI with a card under it, and until end of turn, it gains [[Assassin]]." +
                "~#Target 1 of your opponent's upped SIGNI, and banish it."
        );

		setName("zh_simplified", "解放的弹丸");
        setDescription("zh_simplified", 
                "有下面的牌的你的<<解放派>>精灵1只作为对象，直到回合结束时为止，其得到[[暗杀]]。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1) + Cost.colorless(1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).withCardsUnder(CardUnderCategory.UNDER)));
        }
        private void onSpellEff()
        {
            attachAbility(spell.getTarget(), new StockAbilityAssassin(), ChronoDuration.turnEnd());
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
            banish(target);
        }
    }
}
