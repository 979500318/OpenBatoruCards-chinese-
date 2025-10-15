package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G1_CodeEatKetchacha extends Card {

    public SIGNI_G1_CodeEatKetchacha()
    {
        setImageSets("WXDi-P09-TK01A");

        setOriginalName("コードイート ケチャチャ");
        setAltNames("コードイートケチャチャ Koodo Iito Kechacha");
        setDescription("jp",
                "((このクラフトは効果以外によっては場に出せない))\n" +
                "((【アクセ】はシグニ１体に１枚までしか付けられない。このクラフトが付いているシグニが場を離れるとこのクラフトは除外される))\n\n" +
                "@C：これにアクセされているシグニのパワーを＋10000する。"
        );

        setName("en", "Ketchacha, Code: Eat");
        setDescription("en",
                "((This Craft can only be put onto the field by an effect.))\n" +
                "((Up to one [[Acce]] can be attached to a SIGNI, and it is removed from the game when that SIGNI leaves the field.))\n\n" +
                "@C: The SIGNI with this attached to it as an [[Acce]] gets +10000 power."
        );
        
        setName("en_fan", "Code Eat Ketchacha");
        setDescription("en_fan",
                "((This craft cannot enter the field other than by effects.))\n" +
                "((A SIGNI can only have up to 1 [[Accessory]] attached to it. This craft is excluded from the game when the SIGNI it is attached to leaves the field.))\n\n" +
                "@C: The SIGNI accessorized with this card gets +10000 power."
        );
        
		setName("zh_simplified", "食用代号 番茄汁");
        setDescription("zh_simplified", 
                "@>@C 被此牌附属的精灵的力量+10000。@@\n"
        );

        setCardFlags(CardFlag.CRAFT);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.COOKING);
        setLevel(1);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ConstantAbility cont = registerConstantAbility(new TargetFilter().SIGNI().over(cardId), new PowerModifier(10000));
            cont.setActiveUnderFlags(GameConst.CardUnderType.ATTACHED_ACCESSORY);
        }
    }
}
