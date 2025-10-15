package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_K_GraveUp extends Card {

    public SPELL_K_GraveUp()
    {
        setImageSets("WXDi-P14-069");
        setLinkedImageSets("WXDi-P14-049");

        setOriginalName("グレイブ・アップ");
        setAltNames("グレイブアップ Gureibu Appu");
        setDescription("jp",
                "あなたのトラッシュから黒のシグニ１枚を対象とし、それを場に出す。それが《羅菌姫　ナナシ//フェゾーネ》の場合、それは覚醒する。" +
                "~#：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Grave Up");
        setDescription("en",
                "Put target black SIGNI from your trash onto your field. If it is \"Nanashi//Fesonne, Bacteria Queen\", it is awakened." +
                "~#Add target SIGNI without a #G from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Grave Up");
        setDescription("en_fan",
                "Target 1 black SIGNI from your trash, and put it onto the field. If it is \"Nanashi//Fessone, Natural Bacteria Princess\", it awakens." +
                "~#Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "墓地·竖直");
        setDescription("zh_simplified", 
                "从你的废弃区把黑色的精灵1张作为对象，将其出场。其是《羅菌姫　ナナシ//フェゾーネ》的场合，将其觉醒。（精灵觉醒后在场上保持觉醒状态）" +
                "~#从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));

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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withColor(CardColor.BLACK).fromTrash().playable()));
        }
        private void onSpellEff()
        {
            CardIndex target = spell.getTarget();
            if(putOnField(target) && target.getIndexedInstance().getName().getValue().contains("羅菌姫　ナナシ//フェゾーネ"))
            {
                target.getIndexedInstance().getCardStateFlags().addValue(CardStateFlag.AWAKENED);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            
            if(target != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(target);
                } else {
                    putOnField(target);
                }
            }
        }
    }
}
