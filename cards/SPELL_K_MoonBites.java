package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.*;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.modifiers.ValueByReferenceModifier;

public final class SPELL_K_MoonBites extends Card {

    public SPELL_K_MoonBites()
    {
        setImageSets("WXDi-P05-086");
        setLinkedImageSets("WXDi-P05-042");

        setOriginalName("ムーン・バイツ");
        setAltNames("ムーンバイツ Muun Baitsu");
        setDescription("jp",
                "あなたのトラッシュから＜宇宙＞のシグニ１枚を対象とし、それを手札に加える。その後、それが《羅星姫　イクリプス》の場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。\n\n" +
                "@C：このカードがデッキかトラッシュにあるかぎり、あなたの効果１つによってこのカードを参照する場合、レベル１のシグニとして扱ってもよい。" +
                "~#：対戦相手のレベル２以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Moon Bites");
        setDescription("en",
                "Add target <<Cosmos>> SIGNI from your trash to your hand. Then, if that SIGNI is \"Eclipse, Natural Planet Queen\", target SIGNI on your opponent's field gets --2000 power until end of turn.\n\n" +
                "@C: As long as this card is in your deck or trash, if one of your effects refers to this card, you may treat it as a level one SIGNI." +
                "~#Vanish target level two or less SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Moon Bites");
        setDescription("en_fan",
                "Target 1 <<Space>> SIGNI from your trash, and add it to your hand. Then, if it was \"Eclipse, Natural Star Princess\", target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power.\n\n" +
                "@C: As long as this card is in your deck or trash, if your effect would refer to this card, you may treat is as a level 1 SIGNI." +
                "~#Target 1 of your opponent's level 2 or lower SIGNI, and banish it."
        );

		setName("zh_simplified", "月亮·盈亏");
        setDescription("zh_simplified", 
                "从你的废弃区把<<宇宙>>精灵1张作为对象，将其加入手牌。然后，其是《羅星姫　イクリプス》的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n" +
                "@C :这张牌在牌组或废弃区时，因为你的效果1个把这张牌参照的场合，可以视作等级1的精灵。" +
                "~#对战对手的等级2以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            
            ConstantAbility cont = registerConstantAbility(
                new ValueByReferenceModifier<>(this::onConstEffMod1GetSample, new ValueByReference<>(this::onConstEffModByRefCond, new ValueByReferenceOptions<>(1))),
                new ValueByReferenceModifier<>(this::onConstEffMod2GetSample, new ValueByReference<>(this::onConstEffModByRefCond, new ValueByReferenceOptions<>(CardType.SIGNI)))
            );
            cont.setActiveLocation(CardLocation.DECK_MAIN, CardLocation.TRASH);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.SPACE).fromTrash()));
        }
        private void onSpellEff()
        {
            CardIndex cardIndex = spell.getTarget();
            if(cardIndex != null)
            {
                addToHand(cardIndex);

                if(cardIndex.getIndexedInstance().getName().getValue().contains("羅星姫　イクリプス"))
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                    gainPower(target, -2000, ChronoDuration.turnEnd());
                }
            }
        }
        
        private ModifiableInteger onConstEffMod1GetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getLevel();
        }
        private CardDataType onConstEffMod2GetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getCardType();
        }
        private boolean onConstEffModByRefCond(CardIndex cardIndex, Ability sourceAbility)
        {
            return isOwnCard(sourceAbility.getSourceCardIndex());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,2)).get();
            banish(target);
        }
    }
}
